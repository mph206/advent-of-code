import { parseToLines } from "../utils/file_parsing.ts";

interface sum { numbers: number[], operation: string }
interface sumString { numbers: string[], operation: string }

function partOne(file: string): number {
    const input = parseToLines(file);
    const sums = new Map<number, sum>();
    input.forEach((row) => {
        row.trim().split(/\s+/).forEach((value, index) => {
            const sum = sums.get(index) ?? { numbers: [], operation: "" };
            if (["+", "*"].includes(value)) {
                sums.set(index, { ...sum, operation: value });
            } else {
                sums.set(index, { ...sum, numbers: [...sum.numbers, Number(value)] })
            }
        })
    });
    return sums.values().map((sum) => sum.numbers.reduce((acc, currentNumber) => {
        return sum.operation === '+'
            ? currentNumber + acc
            : currentNumber * acc;
    })).reduce((acc, val) => acc + val);
}

function partTwo(file: string): number {
    const input = parseToLines(file);
    const sums = new Map<number, sumString>();
    const rowLength = input[0].length;

    const currentNumbers: string[] = [];
    let currentOperation = "";
    let index = 0;

    for (let x = 0; x < rowLength; x++) {
        const columnValues: string[] = []
        const operationRowIndex = input.length - 1;
        for (let i = 0; i < operationRowIndex; i++) {
            columnValues.push(input[i][x]);
        }
        if (["+", "*"].includes(input[operationRowIndex][x])) {
            currentOperation = input[operationRowIndex][x];
        }

        columnValues.forEach((val, idx) => {
            if (columnValues.every(e => e === " ")) return;
            currentNumbers[idx] = (currentNumbers[idx] ?? "") + val;
        })
        // We've reach the dividing column or end
        if (columnValues.every(e => e === " ") || (x === rowLength - 1)) {
            sums.set(index, { numbers: [...currentNumbers], operation: currentOperation });
            currentNumbers.length = 0;
            currentOperation = "";
            index++;
        }
    }

    return sums.values().toArray().reverse().map((sum) => transposeNumbers(sum.numbers).reduce((acc, currentNumber) => {
        return sum.operation === '+'
            ? currentNumber + acc
            : currentNumber * acc;
    })).reduce((acc, val) => acc + val);
}

function transposeNumbers(numbers: string[]): number[] {
    const newArray: number[] = [];
    for (let i = numbers[0].length; i > 0; i--) {
        const newNumber = numbers
            .map(num => num.slice(i - 1, i))
            .join("")
            .trim();

        newArray.push(Number(newNumber));
    }
    return newArray;
}

export {
    partOne,
    partTwo  
}
      