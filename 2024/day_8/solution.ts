import { parseFile, stringToGrid } from '../utils.ts';
import { Coordinate } from '../interfaces.ts';

function findUniqueAntinodeLocations(input: string, extendAntinodePositions: boolean): number {
    const grid = stringToGrid(input);
    const map = buildCharCoordinateMap(grid);
    const antinodeLocations = findAntinodes(map, extendAntinodePositions, grid);
    const antinodesInBounds = filterAntinodesInBounds(antinodeLocations, grid);
    return new Set(antinodesInBounds.map(coord => coord.toString())).size;
}

function buildCharCoordinateMap(grid: string[][]) {
    const map = new Map<string, Coordinate[]>();
    grid.forEach((line, lineIndex) => {
        line.forEach((char, charIndex) => {
            if (char === '.') return;
            const mapEntry = map.get(char);
            if (mapEntry !== undefined) {
                mapEntry.push(new Coordinate(charIndex, lineIndex));
            } else {
                map.set(char, [new Coordinate(charIndex, lineIndex)]);
            }
        });
    })
    return map;
}

function findAntinodes(map: Map<string, Coordinate[]>, extendAntinodePositions: boolean, grid: string[][]): Coordinate[] {
    return Array.from(map).flatMap(([_, coordinates]) => {
        return coordinates.flatMap((coordA, i) => {
            return coordinates.slice(i + 1).flatMap((coordB => {
                const newCoords = generateAntinodesForPair(coordA, coordB, extendAntinodePositions, grid);
                return extendAntinodePositions 
                        ? newCoords
                        : newCoords.filter((coord => !(coord.equals(coordA) || coord.equals(coordB))));
            }))
        });
    });
}

function generateAntinodesForPair(coordA: Coordinate, coordB: Coordinate, extendAntinodePositions: boolean, grid: string[][]): Coordinate[] {
    const difference = coordA.subtract(coordB); 
    const antinodePositions = [
        coordB.add(difference), 
        coordB.subtract(difference), 
        coordA.add(difference), 
        coordA.subtract(difference)
    ];

    if (extendAntinodePositions) {
        for (let i = 0; i < Math.max(grid.length, grid[0].length); i++) {
            antinodePositions.push(
                antinodePositions[antinodePositions.length - 4].add(difference), 
                antinodePositions[antinodePositions.length - 3].subtract(difference), 
                antinodePositions[antinodePositions.length - 2].add(difference), 
                antinodePositions[antinodePositions.length - 1].subtract(difference)
            )
        }
    }

    return antinodePositions;
}

function filterAntinodesInBounds(locations: Coordinate[], grid: string[][]) {
    return locations.filter(coord => coord.x >= 0 && coord.y >= 0 && coord.y < grid.length && coord.x < grid[0].length);
}

console.log(findUniqueAntinodeLocations(parseFile("day_8/input.txt"), false)); // 256
console.log(findUniqueAntinodeLocations(parseFile("day_8/input.txt"), true)); // 1005

export {
    findUniqueAntinodeLocations
}