// deno-lint-ignore-file no-case-declarations
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

function calculateRegionPerimeter(region: Coordinate[], adjacencyList: AdjacencyList, grid: string[][]): number {
    // Include all plot sides, then remove the sides that are joined to another plot
    return region.length * 4 - region.reduce((prev, curr) => prev + (adjacencyList.get(curr.toString()) ?? []).length, 0)
}

// Corners are equivalent to sides
function calculateRegionSides(region: Coordinate[], adjacencyList: AdjacencyList, grid: string[][]): number {
    const cornerMap = new Map<string,Set<number>>();
    let duplicateCorners = 0;
    const nonAdjacencyList = buildAdjacencyList(grid, isInvalidPath);
    const corners = region.map(coord => {
        const adjacentCoordsInRegion = adjacencyList.get(coord.toString()) ?? [];
        const adjacentCoordsNotInRegion = nonAdjacencyList.get(coord.toString()) ?? [];
        let cornerCount = 0;
        
        switch (adjacentCoordsInRegion.length) {
            case 0:
                cornerCount += 4;
                break;
            case 1:
                cornerCount += 2;
                break;
            case 2:
                const isParallel = adjacentCoordsInRegion[0].x == adjacentCoordsInRegion[1].x || adjacentCoordsInRegion[0].y == adjacentCoordsInRegion[1].y;
                cornerCount += isParallel ? 0 : 1;
                break;
            default:
                break;
        }

        function findCornerPositions(outsideCoord: Coordinate, insideCoords: Array<Coordinate>): Set<number> {
            const corners = new Set<number>();
            const diffToEdge = new Map([
                ["{x: 0, y: -1}", "TOP"],
                ["{x: 0, y: 1}", "BOTTOM"],
                ["{x: 1, y: 0}", "RIGHT"],
                ["{x: -1, y: 0}", "LEFT"],
            ]);
            const edges = insideCoords
                .map(insideCoord => insideCoord.subtract(outsideCoord))
                .map(diff => diffToEdge.get(diff.toString())!);
            const edgeToCorner = new Map([
                [["LEFT", "TOP"], 1],
                [["TOP", "RIGHT"], 2],
                [["RIGHT", "BOTTOM"], 3],
                [["BOTTOM", "LEFT"], 4]
            ]);
            edgeToCorner.forEach((value, key) => {
                if (edges.some(edge => edge == key[0]) && edges.some(edge => edge == key[1])) {
                    corners.add(value);
                }
            });
            return corners;
        }

        // Find concave corners
        adjacentCoordsNotInRegion.forEach((coordNotInRegion) => {
            const insideCoords = (nonAdjacencyList.get(coordNotInRegion.toString()) ?? []).filter(it => region.some(regionCoord => regionCoord.x == it.x && regionCoord.y == it.y));

            switch (insideCoords.length) {
                case 2: 
                case 3: 
                    const positions = findCornerPositions(coordNotInRegion, insideCoords);
                    if (positions.size > 0) {
                        const values = positions.values();
                        cornerMap.set(coordNotInRegion.toString(), new Set([...(cornerMap.get(coordNotInRegion.toString()) ?? new Set()), ...values]));
                    }
                    break;
                case 4:
                    cornerMap.set(coordNotInRegion.toString(), new Set([1, 2, 3, 4]));
                    break;;
                default: 
                    break;
            }
        })

        return cornerCount;
    }).reduce((prev: number, curr: number) => prev + curr, 0);

    // Filter out diagonal touching corners
    region.forEach(coord => {
        const thisCoordNonAdjacents = (nonAdjacencyList.get(coord.toString()) ?? [])
            .sort((a, b) => a.y - b.y)
            .sort((a, b) => a.x - b.x);;
        if (thisCoordNonAdjacents.length === 2) {
            if (region
                .filter(regionCoord => !regionCoord.equals(coord))
                .some(otherCoord => {
                    const otherCoordNonAdjacents = (nonAdjacencyList.get(otherCoord.toString()) ?? [])
                            .sort((a, b) => a.y - b.y)
                            .sort((a, b) => a.x - b.x);
                    if (otherCoordNonAdjacents.length === 2) {
                        return otherCoordNonAdjacents.every((other, index) => thisCoordNonAdjacents[index].equals(other));
                    };
                    return false;
                })) {
                    duplicateCorners++;
            }
        }
    });
    
    const concaveCorners = Array.from(cornerMap.values()).reduce((acc, curr) => acc + curr.size, 0);
    return corners + concaveCorners - duplicateCorners;
}

const isValidPath = (char: string, coord: Coordinate, grid: string[][]) => char === grid[coord.y][coord.x];

const isInvalidPath = (char: string, coord: Coordinate, grid: string[][]) => char !== grid[coord.y][coord.x];

console.log(partOne(parseFile("day_12/input.txt"), calculateRegionPerimeter)); // 1421958
console.log(partOne(parseFile("day_12/input.txt"), calculateRegionSides)); // too high: 890808

export {
    partOne,
    calculateRegionPerimeter, 
    calculateRegionSides
}
