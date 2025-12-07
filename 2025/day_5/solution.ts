import { parseToLines } from '../utils/file_parsing.ts';

interface range { start: bigint, end: bigint }

function getRangesAndNumbers(input: string[]) {
    const ranges: range[] = []
    const numbers: number[] = []
    input.forEach((line) => {
        if (line.search("-") > 0) {
            const start = BigInt(line.split("-")[0])
            const end = BigInt(line.split("-")[1])
            ranges.push({ start, end });
        } else if (line.length === 0) {
            return;
        } else {
            numbers.push(Number(line))
        }
    });
    return { ranges, numbers }
}

function partOne(input: string[]): number {
    const { ranges, numbers } = getRangesAndNumbers(input);

    const valid = numbers.map((number) => {
        return ranges.some((range) => {
            return range.start <= number && range.end >= number
        })
    })

    return valid.filter((v) => v).length;
}

function partTwo(input: string[]): bigint {
    let { ranges: oldRanges } = getRangesAndNumbers(input);
    let newRanges: range[] = []

    let rangesWereUpdated: boolean;
    do {
        rangesWereUpdated = false;
        oldRanges.forEach((oldRange) => {
            const newRange = newRanges.find((newRange) => {
                const overlaps = oldRange.start <= newRange.end && newRange.start <= oldRange.end;
                return overlaps;
            });

            if (newRange === undefined) {
                newRanges.push(oldRange);
                return;
            }
            if (oldRange.start < newRange.start) {
                newRange.start = oldRange.start;
                rangesWereUpdated = true;
            }
            if (oldRange.end > newRange.end) {
                newRange.end = oldRange.end;
                rangesWereUpdated = true;
            }
        });
        oldRanges = [...newRanges];
        newRanges = [];
    } while (rangesWereUpdated);

    return oldRanges.map((range) => range.end - range.start + BigInt(1)).reduce((acc, int) => acc + int);
}

console.log(partOne(parseToLines("day_5/input.txt"))); 
console.log(partTwo(parseToLines("day_5/input.txt")));
