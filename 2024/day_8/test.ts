import { assertEquals } from "jsr:@std/assert";
import { findUniqueAntinodeLocations } from "./solution.ts";

Deno.test("simple test", () => {
    const input = 
    `..........
    ..........
    ..........
    ....a.....
    ..........
    .....a....
    ..........
    ..........
    ..........
    ..........`
    
    const output = findUniqueAntinodeLocations(input, false);
    assertEquals(output, 2);
});

Deno.test("simple test", () => {
    const input = 
    `..........
    ..........
    ..........
    ....a.....
    ........a.
    .....a....
    ..........
    ..........
    ..........
    ..........`
    
    const output = findUniqueAntinodeLocations(input, false);
    assertEquals(output, 4);
});

Deno.test("simple test", () => {
    const input = 
    `..........
    ..........
    ..........
    ....a.....
    ........a.
    .....a....
    ..........
    ......A...
    ..........
    ..........`
    
    const output = findUniqueAntinodeLocations(input, false);
    assertEquals(output, 4);
});

Deno.test("simple test", () => {
    const input = 
    `............
    ........0...
    .....0......
    .......0....
    ....0.......
    ......A.....
    ............
    ............
    ........A...
    .........A..
    ............
    ............`
    
    const output = findUniqueAntinodeLocations(input, false);
    assertEquals(output, 14);
});

Deno.test("simple test", () => {
    const input = 
    `............
    ........0...
    .....0......
    .......0....
    ....0.......
    ......A.....
    ............
    ............
    ........A...
    .........A..
    ............
    ............`
    
    const output = findUniqueAntinodeLocations(input, true);
    assertEquals(output, 34);
});