import { Coordinate } from "../utils/interfaces.ts";
import { stringToGrid, parseFile } from "../utils/file_parsing.ts";
import { buildAdjacencyList, dfs, AdjacencyList } from "../utils/dfs.ts";

function partOne(input: string): number {
    const grid = stringToGrid(input);
    const adjacencyList = buildAdjacencyList(grid, isValidPath);
    const regions = buildRegions(grid, adjacencyList);
    return scoreRegions(regions, adjacencyList);
}

// This is slow (about 2 mins for full input)
function buildRegions(grid: string[][], adjacencyList: AdjacencyList): Coordinate[][] {
    
    const paths = grid.flatMap((line, lineIndex) => {
        return line.map((_, charIndex) => {
            return dfs(adjacencyList, new Coordinate(charIndex, lineIndex), false);
        })
    });
    const uniquePaths = paths
        .map(path => path.filter((aCoord, coordIndex) => {
            return !path.slice(coordIndex + 1).some(bCoord => bCoord.toString() === aCoord.toString())
        })
            .sort((a, b) => a.y - b.y)
            .sort((a, b) => a.x - b.x)
        )
        .filter((currentPath, pathIndex, thisArray) => {
            return !thisArray.slice(pathIndex + 1).some((path => path.toString() === currentPath.toString()))
        });
    return uniquePaths;
}

function scoreRegions(regions: Coordinate[][], adjacencyList: AdjacencyList): number {
    return regions.map((region) => {
        return calculateRegionArea(region) * calculateRegionPerimeter(region, adjacencyList)
    }).reduce((prev, curr) => prev + curr);
}

const calculateRegionArea = (region: Coordinate[]) => region.length;

function calculateRegionPerimeter(region: Coordinate[], adjacencyList: AdjacencyList): number {
    return region.length * 4 - region.reduce((prev, curr) => prev + (adjacencyList.get(curr.toString()) ?? []).length, 0)
}

const isValidPath = (char: string, coord: Coordinate, grid: string[][]) => char === grid[coord.y][coord.x];

// part two - calculate number of sides 
console.log(partOne(parseFile("day_12/input.txt"))); // 1421958

export {
    partOne
}