import { parseFile } from "../utils/file_parsing.ts";

type Coord = { x: number, y: number }

type Machine = { a: Coord, b: Coord, prize: Coord, cost?: number, aCount?: number, bCount?: number }

function partOne(filePath: string): number {
    const machines = parseInput(filePath);
    const gamePossiblities = generatePossibilities(machines);
    return gamePossiblities.reduce((a, b) => a + b, 0);
}

function parseInput(filePath: string): Array<Machine> {
    return parseFile(filePath).split("\n\n").map(group => {
        const coords = group.split("\n").map(line => {
            const numbers = [...line.matchAll(/(\d+)/g)];
            return {x: Number(numbers[0][0]), y: Number(numbers[1][0])}
        });
        return { a: coords[0], b: coords[1], prize: coords[2]};
    });
}

function generatePossibilities(machines: Array<Machine>): Array<number>  {
    return machines.map(machine => {
        const possibilities = new Array<Machine>();

        for (let i = 0; i <= 100; i++) {
            const aX = machine.a.x * i;
            const aY = machine.a.y * i;
            if (aX <= machine.prize.x 
                && aY <= machine.prize.y
                && (machine.prize.x - aX) % machine.b.x === 0 
                && (machine.prize.x - aX) / machine.b.x <= 100
                && (machine.prize.y - aY) / machine.b.y <= 100
                && ((machine.prize.y - aY) / machine.b.y) === ((machine.prize.x - aX) / machine.b.x)
            )  {
                possibilities.push({...machine, cost: 3 * i + (machine.prize.x - aX) / machine.b.x, aCount: i, bCount: (machine.prize.x - aX) / machine.b.x});
            }
        }
        return possibilities.length > 0 ? Math.min(...possibilities.map(it => it.cost!)) : 0;
    })
}

console.log(partOne("day_13/input_test.txt")); // 33427

export {
    partOne,
}
