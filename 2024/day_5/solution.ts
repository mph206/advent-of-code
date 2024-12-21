import { parseFile } from '../utils.ts';

function partOne(input: string): number {
    const { rulesMap, splitUpdates } = parseInput(input);
    const validUpdates: number[][] = [];

    splitUpdates.forEach(update => {
        if (update.every((item, index) => {
            const afters = rulesMap.get(item)
            return afters !== undefined
                ? afters
                    .values()   
                    .map(after => update.indexOf(after))
                    .every(afterIndex => {
                        return afterIndex === -1 || (afterIndex > index)
                    })
                : true
        })) {
            validUpdates.push(update);
        }
    })
    
    return sumMiddleNumbers(validUpdates);
}

function partTwo(input: string): number {
    const { rulesMap, splitUpdates } = parseInput(input);

    const validUpdates = splitUpdates.map(update => {
        if (update.every((item, index) => {
            const afters = rulesMap.get(item)
            return afters !== undefined
                ? afters
                    .values()   
                    .map(after => update.indexOf(after))
                    .every(afterIndex => {
                        return afterIndex === -1 || (afterIndex > index)
                    })
                : true
        })) {
            return []
        } else {
            // This update is invalid - sort using the rules
            return update.toSorted((a, b) => {
                const aftersForA = rulesMap.get(a);
                const aftersForB = rulesMap.get(b);
                if (aftersForA !== undefined && aftersForA.has(b)) {
                    return -1;
                }
                if (aftersForB !== undefined && aftersForB.has(a)) {
                    return 1;
                }
                return 0;
            })
        }
    }).filter(it => it.length !== 0)
    
    return sumMiddleNumbers(validUpdates);
}

function parseInput(input: string) {
    const [ rules, updates ] = parseFile(input).split("\n\n").map(section => section.split("\n"));
    const rulesMap: Map<number, Set<number>> = new Map(
        rules.map(rule => [Number(rule.split("|")[0]), new Set()])
    );
    rules.map(rule => {
        const key = Number(rule.split("|")[0])
        const value = Number(rule.split("|")[1])
        return [key, value];
    }).forEach(rule => rulesMap.get(rule[0])?.add(rule[1]))

    const splitUpdates = updates.map(update => update.split(",").map(string => Number(string)));

    return {rulesMap, splitUpdates}
}

function sumMiddleNumbers(array: number[][]): number {
    return array.map(item => {
        const middleIndex = Math.ceil(item.length / 2) - 1;
        return item[middleIndex];
    }).reduce((a, b) => a + b);
}

console.log(partOne("day_5/input.txt")); // 5588
console.log(partTwo("day_5/input.txt")); // 5331