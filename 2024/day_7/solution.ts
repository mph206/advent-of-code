import { parseToLines } from '../utils.ts';

/* We can think of this problem as a graph
            81
       x40       +40 
    x27  +27  x27   +27
*/
function calculateEquations(input: string, includeConcatenationOperations: boolean) : number {
    const equations = parseInput(input)
    return equations.map(equation => {
        let currentSumValues = [equation.numbers[0]];
        const nextSumValues: number[] = [];
        equation.numbers.slice(1).forEach((number, index) => {
            currentSumValues.forEach(value => {
                nextSumValues.push(value * number);
                nextSumValues.push(value + number);

                if (includeConcatenationOperations) {
                    const nextNumber = equation.numbers[index + 1];
                    nextSumValues.push(Number(String(value) + String(nextNumber)));
                }                
            });
            currentSumValues = [...nextSumValues];
            nextSumValues.length = 0;
        });
        return currentSumValues.some(value => value === equation.value)
            ? equation.value 
            : 0;
    })
    .reduce((a, b) => a + b)
}

function parseInput(input: string): Equation[] {
    return parseToLines(input).map(line => {
        const split = line.split(":").map(part => part.trim().split(" ").map(string => Number(string)));
        return new Equation(split[0][0], split[1])
    });
}

class Equation {
    value: number;
    numbers: number[];

    constructor(value: number, numbers: number[]) {
        this.value = value;
        this.numbers = numbers;
      }
}

console.log(calculateEquations("day_7/input.txt", false)); // 6392012777720
console.log(calculateEquations("day_7/input.txt", true)); // 61561126043536