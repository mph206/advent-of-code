import { parseFile } from "../utils/file_parsing.ts";

function parseInput (input: string): number[][] {
    const intermediate = parseFile(input)
        .split("\n")
        .map(item => item.split("  ")
        .map(string => Number(string)));
    let listOne:number[] = []
    let listTwo: number[] = []
    intermediate.forEach((item: number[]) => {
        listOne.push(item[0]) 
        listTwo.push(item[1]) 
    });
    return [listOne, listTwo]
}

const listOne = parseInput("day_1_input.txt")[0]
const listTwo = parseInput("day_1_input.txt")[1]

function orderListsAndSumDifference (listOne: number[], listTwo: number[]) {
    const differenceBetweenLists = listTwo.map((num, index) => {
        return Math.abs(num - listOne[index])
    });
    return differenceBetweenLists.reduce((previous, current) => previous + current);
}


function calculateSimilaryScore(listOne: number[], listTwo: number[]) {
    const counts = listOne.map(item => item * listTwo.filter(num => num === item).length)
    return counts.reduce((prev, curr) => prev + curr)
}

console.log(orderListsAndSumDifference(listOne, listTwo)) // 28524731
console.log(calculateSimilaryScore(listOne, listTwo)) // 23384288
