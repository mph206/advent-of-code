import { parseFile } from '../utils.ts';

function parseInput (input: string): number[][] {
    return parseFile(input).split("\n").map(item => item.split(" ").map(string => Number(string)))
}

function partOne(reports: number[][]) {
    const differenceBetweenLists = reports.map((report) => {
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
    });
    return differenceBetweenLists.filter((value) => value).length;
}

function partTwo(reports: number[][]) {
    return reports
        .map(report => {
            let ascending = null;
            let errorCount = 0;
            const currentReport = report.slice();

            // Problem if we have to remove the start or end character to fix the sequence
            // 36 35 39 42 43 46 47
            // 1 3 5 6 8 9 12 9

            for (let i = 0; i < currentReport.length; i++) {
                const difference = currentReport[i + 1] - currentReport[i];
                const absoluteDifference = Math.abs(difference)
                if (errorCount > 1) {
                    return false;
                }

                if (difference < 0) {
                    if (ascending) {
                        errorCount++;
                        currentReport.splice(i, 1);
                        i = -1;
                        ascending = null
                        continue;
                    }
                    ascending = false
                } else if (difference > 0) {
                    if (ascending === false) {
                        errorCount++;
                        currentReport.splice(i, 1);
                        ascending = null
                        i = -1
                        continue;
                    }
                    ascending = true
                }

                if (absoluteDifference < 1 || absoluteDifference > 3) {
                    errorCount++;
                    currentReport.splice(i, 1);
                    ascending = null
                    i = -1
                    continue;
                }

                if (errorCount > 1) {
                    return false;
                }
            }
            return true
        })
        // .filter((value) => value)
        // .length;
}

// console.log(partOne(parseInput("day_2/input.txt"))); // 359
console.log(partTwo(parseInput("day_2/input.txt"))); // 398 is too low

// false, true,  false, false, false, false, false, false,
//   false, true,  false, false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, false, false, false, false, false, true,
//   false, false, false, false, false, false, false, false,
//   false, true,  false, false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, true,  false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, false, false, false, false, false, false,
//   false, false, false, true,