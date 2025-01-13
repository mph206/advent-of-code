import { Coordinate } from "../utils/interfaces.ts";
import { stringToGrid, parseFile } from "../utils/file_parsing.ts";
import { buildAdjacencyList, dfs, AdjacencyList } from "../utils/dfs.ts";

type LineScoringFunction = (regions: Coordinate[], adjacencyList: AdjacencyList, grid: string[][]) => number;

function partOne(input: string, lineScoringFunction: LineScoringFunction): number {
    const grid = stringToGrid(input);
    const adjacencyList = buildAdjacencyList(grid, isValidPath);
    const regions = buildRegions(grid, adjacencyList);
    return scoreRegions(regions, adjacencyList, grid, lineScoringFunction);
}

function buildRegions(grid: string[][], adjacencyList: AdjacencyList): Coordinate[][] {
    const paths = grid.flatMap((line, lineIndex) => {
        return line.map((_, charIndex) => {
            return dfs(adjacencyList, new Coordinate(charIndex, lineIndex), false);
        })
    });

    const uniquePaths = new Array<Coordinate[]>;
    paths.forEach((path) => {
        if (!uniquePaths.some(uniquePath => uniquePath.some(coord => coord.equals(path[0]!)))) {
            const uniquePath = new Array<Coordinate>();
            path.forEach((coord) => {
                if (!uniquePath.some(pathCoord => pathCoord.equals(coord))) {
                    uniquePath.push(coord);
                }
            })
            uniquePaths.push(uniquePath);
        }
    })

    return uniquePaths;
}

function scoreRegions(regions: Coordinate[][], adjacencyList: AdjacencyList, grid: string[][], scoreLines: LineScoringFunction): number {
    return regions.map((region) => {
        return calculateRegionArea(region) * scoreLines(region, adjacencyList, grid)
    }).reduce((prev, curr) => prev + curr);
}

const calculateRegionArea = (region: Coordinate[]) => region.length;

function calculateRegionPerimeter(region: Coordinate[], adjacencyList: AdjacencyList, _: string[][]): number {
    // Include all plot sides, then remove the sides that are joined to another plot
    return region.length * 4 - region.reduce((prev, curr) => prev + (adjacencyList.get(curr.toString()) ?? []).length, 0)
}

function calculateRegionSides(region: Coordinate[], _: AdjacencyList, grid: string[][]): number {
    let topCount = 0;
    let bottomCount = 0;
    let leftCount = 0;
    let rightCount = 0;

    for (let i = 0; i < region.length; i++) {
        const current = region[i];
        const y = current.y;
        const x = current.x;
        const char = grid[y][x];

        if ((grid[y - 1] === undefined || grid[y - 1][x] !== char) 
            && (((grid[y][x - 1] ?? "false") !== char) || (grid [y - 1] !== undefined && ((grid[y - 1][x - 1] ?? "false") === char)))) {
            topCount++;
        }

        if ((grid[y + 1] === undefined || grid[y + 1][x] !== char) 
            && (((grid[y][x - 1] ?? "false") !== char) || (grid[y + 1] !== undefined && ((grid[y + 1][x - 1] ?? "false") === char)))) {
            bottomCount++;
        }

        if (((grid[y][x - 1] ?? "false") !== char) 
            && (grid[y - 1] === undefined || ((grid[y - 1][x] ?? "false") !== char) || ((grid[y - 1][x - 1] ?? "false") === char))) {
            leftCount++;
        }

        if (((grid[y][x + 1] ?? "false") !== char) 
            && (grid[y - 1] === undefined || ((grid[y - 1][x] ?? "false") !== char) || ((grid[y - 1][x + 1] ?? "false") === char))) {
            rightCount++;
        }
    }

    return topCount + bottomCount + leftCount + rightCount;
}

const isValidPath = (char: string, coord: Coordinate, grid: string[][]) => char === grid[coord.y][coord.x];

console.log(partOne(parseFile("day_12/input.txt"), calculateRegionPerimeter)); // 1421958
console.log(partOne(parseFile("day_12/input.txt"), calculateRegionSides)); // 885394

export {
    partOne,
    calculateRegionPerimeter, 
    calculateRegionSides
}
