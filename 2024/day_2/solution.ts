import { parseFile } from '../utils/file_parsing.ts';

function parseInput (input: string): number[][] {
    return parseFile(input).split("\n").map(item => item.split(" ").map(string => Number(string)))
}

function partOne(reports: number[][]) {
    const differenceBetweenLists = reports.map((report) => isValidReport(report));
    return differenceBetweenLists.filter((value) => value).length;
}

function isValidReport(report: number[]): boolean {
    const sortedReport = report.slice().sort((a, b) => a - b)
    if (sortedReport.every((val, idx) => val === report[idx]) 
    || sortedReport.slice().reverse().every((val, idx) => val === report[idx])) {
        const differences: number[] = []
        for (let i = 1; i < sortedReport.length; i++) {
            differences.push(sortedReport[i] - sortedReport[i - 1])
        }
        return differences.every((value) => value <= 3 && value >= 1)
    }
    return false
}

function partTwo(reports: number[][]) {
    return reports
        .map(report => {
            const reportsToCheck = [report];

            if (isValidReport(report)) {
                return true
            }

            reportsToCheck.push(...generatePermutations(report));

            while (reportsToCheck.length !== 0) {
                if (isValidReport(reportsToCheck[reportsToCheck.length - 1])) {
                    return true
                }
                reportsToCheck.pop();                
            }
            return false
        })
        .filter((value) => value)
        .length;
}

function generatePermutations(report: number[]): number[][] {
    return report.map((_, index) => {
        return report.toSpliced(index, 1);
    })
}

console.log(partOne(parseInput("day_2/input.txt"))); // 359
console.log(partTwo(parseInput("day_2/input.txt"))); // 418
