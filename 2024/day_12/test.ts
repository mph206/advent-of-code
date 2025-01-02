import { assertEquals } from "jsr:@std/assert";
import { partOne } from "./solution.ts";

Deno.test("simple test", () => {
    const input = 
    `AAAA
    BBCD
    BBCC
    EEEC`
    
    const output = partOne(input);
    assertEquals(output, 140);
});

Deno.test("simple test", () => {
    const input = 
    `OOOOO
    OXOXO
    OOOOO
    OXOXO
    OOOOO`
    
    const output = partOne(input);
    assertEquals(output, 772);
});

Deno.test("simple test", () => {
    const input = 
    `RRRRIICCFF
    RRRRIICCCF
    VVRRRCCFFF
    VVRCCCJFFF
    VVVVCJJCFE
    VVIVCCJJEE
    VVIIICJJEE
    MIIIIIJJEE
    MIIISIJEEE
    MMMISSJEEE`
    
    const output = partOne(input);
    assertEquals(output, 1930);
});