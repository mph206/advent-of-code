import { parseToLines } from '../utils/file_parsing.ts';

function partOne (input: string): number {
    const batteryBanks = parseToLines(input)
    return batteryBanks.map((bank) => {
        const startList = bank.substring(0, bank.length - 1).split("").map((char) => Number(char));
        const start = Math.max(...startList);
        const startIndex = startList.indexOf(Math.max(...startList));
        const endList = bank.substring(startIndex + 1).split("").map((char) => Number(char));
        const end = Math.max(...endList);

        return Number(start.toString() + end.toString());
    }).reduce((acc, next) => acc + next);
}

function partTwo (input: string): number {
    const batteryBanks = parseToLines(input)
    return batteryBanks.map((bank) => {
        let finalNumber: string = "";
        let startIndex: number = 0;
        for (let i = 11; i >= 0; i--) {
            const startList = bank.substring(startIndex, bank.length - i).split("").map((char) => Number(char));
            const start = Math.max(...startList);
            startIndex += startList.indexOf(start) + 1;
            finalNumber += start.toString();
        }
        return Number(finalNumber);
    }).reduce((acc, next) => acc + next);
}

console.log(partOne("day_3/input_test.txt")) // 357
console.log(partOne("day_3/input.txt")) // 17343
console.log(partTwo("day_3/input_test.txt")) // 3121910778619
console.log(partTwo("day_3/input.txt")) // 172664333119298
