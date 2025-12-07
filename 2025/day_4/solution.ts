import { Coordinate } from "../utils/interfaces.ts";
import { parseFile, stringToGrid } from '../utils/file_parsing.ts';
import { isInBounds } from "../utils/dfs.ts";

function countRollsThatCanBeAccessed(input: string): number {
    const grid = stringToGrid(parseFile(input));
    const rollAdjacencyList = buildRollAdjacencyList(grid);
    const adjacentValuesList = rollAdjacencyList.values().toArray().map((coord) => {
        const adjacentValues: string[] = coord.map((adjacentCoord: Coordinate) => grid[adjacentCoord.y][adjacentCoord.x]);
        return adjacentValues;
    });
    const list = adjacentValuesList.filter((values) => values.filter((char) => char === "@").length < 4);
    return list.length;
}

function countRollsThatCanBeAccessedIfPreviousRollsAreRemoved(input: string): number {
    let grid = stringToGrid(parseFile(input));
    const keysToRemove: string[] = [];
    let rollsRemoved = 0;

    do {
        keysToRemove.length = 0;
        const rollAdjacencyList = buildRollAdjacencyList(grid);
        rollAdjacencyList.forEach((value, key) => {
            const adjacentValues = value.map((coord: Coordinate) => grid[coord.y][coord.x]);
            if (adjacentValues.filter((char) => char === "@").length < 4) {
                keysToRemove.push(key);
            }
        });
        rollsRemoved += keysToRemove.length;
        grid = rebuildGrid(grid, keysToRemove);
    } while (keysToRemove.length > 0);

    return rollsRemoved;
}

function rebuildGrid(grid: string[][], keysToRemove: string[]): string[][] {
    const oldGrid = [...grid];
    const newGrid = oldGrid.map((row, yIndex) => {
        return row.map((item, xIndex) => {
            return keysToRemove.includes(`{x: ${xIndex}, y: ${yIndex}}`)
                ? "x"
                : item;
        })
    })
    return newGrid;
}

function buildRollAdjacencyList(grid: string[][]): Map<string, Coordinate[]> {
    const map: Map<string, Coordinate[]> = new Map();
    grid.forEach((line, lineIndex) => {
        line.forEach((char, charIndex) => {
            if (char !== '@') return;
            map.set(
                new Coordinate(charIndex, lineIndex).toString(), 
                [
                    new Coordinate(charIndex, lineIndex - 1), 
                    new Coordinate(charIndex, lineIndex + 1), 
                    new Coordinate(charIndex + 1, lineIndex), 
                    new Coordinate(charIndex - 1, lineIndex), 
                    new Coordinate(charIndex - 1, lineIndex - 1), 
                    new Coordinate(charIndex + 1, lineIndex + 1), 
                    new Coordinate(charIndex + 1, lineIndex - 1), 
                    new Coordinate(charIndex - 1, lineIndex + 1), 
                ]
                    .filter(coord => isInBounds(coord, grid))
            );
        });
    });
    return map;
}

console.log(countRollsThatCanBeAccessed("day_4/input.txt")) // 1527
console.log(countRollsThatCanBeAccessedIfPreviousRollsAreRemoved("day_4/input.txt")) // 8690
