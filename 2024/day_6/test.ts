import { assertEquals } from "jsr:@std/assert";
import { partTwo } from "./solution.ts";

Deno.test("simple test", () => {
    const input = 
    `....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...`
    
    const output = partTwo(input);
    assertEquals(output, 6);
});

Deno.test("Obstacle must be placed from the beginning", () => {
    const input = 
    `.##..
    ....#
    .....
    .^.#.
    .....`
    
    const output = partTwo(input);
    assertEquals(output, 1);
});

Deno.test("simple test", () => {
    const input = 
    `.#....
    .....#
    #..#..
    ..#...
    .^...#
    ....#.`
    
    const output = partTwo(input);
    assertEquals(output, 3);
});
