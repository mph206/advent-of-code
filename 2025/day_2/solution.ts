import { parseFile } from '../utils/file_parsing.ts';

function parseInput(input: string): number[][] {
    return parseFile(input).split(",").map(item => item.split("-").map(string => Number(string)))
}

function partOne(idList: number[][]): number {
    const invalidIds = idList.flatMap((pair) => {
        const start = pair[0];
        const end = pair[1];
        const range = Array.from({ length: end - start + 1 }, (_, i) => start + i);
        return range
            .map((number) => {
                const string = number.toString();
                const firstHalf = string.substring(0, string.length / 2);
                const secondHalf = string.substring(string.length / 2);
                return firstHalf === secondHalf ? number: null;
            })
            .filter((item) => item !== null)
    })

    return invalidIds.reduce((acc, val) => acc + val, 0);
}

function partTwo(idList: number[][]): number {
    const invalidIds = idList.flatMap((pair) => {
        const start = pair[0];
        const end = pair[1];
        const range = Array.from({ length: end - start + 1 }, (_, i) => start + i);
        return range
            .map((number) => {
                const regex = /^(\d+)\1+$/g;
                const matches = number.toString().match(regex);
                return matches !== null ? Number(matches[0]) : null
            })
            .filter((item) => item !== null)
    })

    return invalidIds.reduce((acc, val) => acc + val, 0);
}

console.log(partOne(parseInput("day_2/input_test.txt"))); // 1227775554
console.log(partOne(parseInput("day_2/input.txt"))); // 23701357374
console.log(partTwo(parseInput("day_2/input_test.txt"))); // 4174379265
console.log(partTwo(parseInput("day_2/input.txt"))); // 34284458938
