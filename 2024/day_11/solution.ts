import { parseFile } from '../utils.ts';

function transformStones(input: string, blinks: number): number {
    let stones = input.split(" ").map(it => Number(it));
    for (let i = 0; i < blinks; i++) {
        stones = performBlink(stones)
    }
    return stones.length;
}

function performBlink(stones: number[]): number[] {
    const newStones: number[] = [];
    stones.forEach(stone => {
        if (stone === 0) {
            newStones.push(1)
        } else if (stone.toString().length % 2 === 0) {
            const stoneString = stone.toString();
            const midwayPoint = stoneString.length / 2;
            const stoneTwo = Number(stoneString.slice(midwayPoint)); // trim leading 0s
            newStones.push(Number(stoneString.slice(0, midwayPoint)), stoneTwo);
        } else {
            newStones.push((Number(stone) * 2024))
        }
    });
    return newStones;
}

console.log(transformStones(parseFile("day_11/input.txt"), 25)); // 209412
console.log(transformStones(parseFile("day_11/input.txt"), 75)); // 

export {
    transformStones
}