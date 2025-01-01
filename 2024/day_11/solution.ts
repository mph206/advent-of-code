import { parseFile } from '../utils.ts';

function transformStones(input: string, blinks: number): number {
    let stoneMap = new Map<number, number>();
    input.split(" ").forEach(it => {
        const currentValue = stoneMap.get(Number(it));
        stoneMap.set(Number(it), currentValue ? currentValue + 1 : 1);
    });
    for (let i = 0; i < blinks; i++) {
        stoneMap = performBlink(stoneMap)
    }
    return Array.from(stoneMap.values()).reduce((prev, curr) => prev + curr, 0);
}

function performBlink(stoneMap: Map<number, number>): Map<number, number> {
    const newStoneMap = new Map<number, number>();
    stoneMap.forEach((count, stone) => {
        if (stone === 0) {
            const existingCount = newStoneMap.get(1);
            newStoneMap.set(1, existingCount ? existingCount + count : count)
        } else if (stone.toString().length % 2 === 0) {
            const stoneString = stone.toString();
            const midwayPoint = stoneString.length / 2;
            const stoneOne = Number(stoneString.slice(0, midwayPoint));
            const stoneTwo = Number(stoneString.slice(midwayPoint)); // trim leading 0s

            const stoneOneValue = newStoneMap.get(stoneOne);
            newStoneMap.set(stoneOne, stoneOneValue ? stoneOneValue + count : count);

            const stoneTwoValue = newStoneMap.get(stoneTwo);
            newStoneMap.set(stoneTwo, stoneTwoValue ? stoneTwoValue + count : count);
        } else {
            const newValue = stone * 2024;
            const existingCount = newStoneMap.get(newValue);
            newStoneMap.set(newValue, existingCount ? existingCount + count : count);
        }
    });
    return newStoneMap;
}

console.log(transformStones(parseFile("day_11/input.txt"), 25)); // 209412
console.log(transformStones(parseFile("day_11/input.txt"), 75)); // 248967696501656
