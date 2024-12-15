import { parseFile } from '../utils.ts';

function partOne (input: string): number {
    const intermediate = parseFile(input)
    const regex = /(mul\(\d{1,3},\d{1,3}\))/g
    const array = Array.from(intermediate.matchAll(regex)).map(item => item[0].substring(4, item[0].length-1).split(","));
    return array.map(item => Number(item[0]) * Number(item[1])).reduce((prev, curr) => prev + curr)
}

function partTwo(input: string) : number {
    const intermediate = parseFile(input)
    const matchDosDontsMuls = /(don't)|(do)|(mul\(\d{1,3},\d{1,3}\))/g
    const array = Array.from(intermediate.matchAll(matchDosDontsMuls)).map(item => item[0].toString())
    const newArray: any[] = []
    let include = true
    for (let i = 0; i < array.length; i++) {
        if (array[i] === "don't") {
            include = false
        } else if (array[i] === "do") {
            include = true;
        } else if (include) {
            newArray.push(array[i]);
        }
    }
    const final = newArray.map(item => item.substring(4, item.length-1).split(","));
    return final.map(item =>  Number(item[0]) * Number(item[1])).reduce((prev, curr) => prev + curr)
}

console.log(partOne("day_3/input.txt"))
console.log(partTwo("day_3/input.txt"))
