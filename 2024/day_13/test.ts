import { assertEquals } from "jsr:@std/assert";
import { partOne, calculateRegionPerimeter, calculateRegionSides } from "./solution.ts";

Deno.test("simple test", () => {
    const input = 
    `AAAA
    BBCD
    BBCC
    EEEC`
    
    const output = partOne(input, calculateRegionPerimeter);
    assertEquals(output, 140);
});

Deno.test("simple test", () => {
    const input = 
    `OOOOO
    OXOXO
    OOOOO
    OXOXO
    OOOOO`
    
    const output = partOne(input, calculateRegionPerimeter);
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
    
    const output = partOne(input, calculateRegionPerimeter);
    assertEquals(output, 1930);
});

Deno.test("simple test", () => {
    const input = 
    `AAAA
    BBCD
    BBCC
    EEEC`
    
    const output = partOne(input, calculateRegionSides);
    assertEquals(output, 80);
});

Deno.test("simple test", () => {
    const input = 
    `OOOOO
    OXOXO
    OOOOO
    OXOXO
    OOOOO`
    
    const output = partOne(input, calculateRegionSides);
    assertEquals(output, 436);
});

Deno.test("simple test", () => {
    const input = 
    `EEEEE
    EXXXX
    EEEEE
    EXXXX
    EEEEE`
    
    const output = partOne(input, calculateRegionSides);
    assertEquals(output, 236);
});

Deno.test("simple test", () => {
    const input = 
    `AAAAAA
    AAABBA
    AAABBA
    ABBAAA
    ABBAAA
    AAAAAA`
    
    const output = partOne(input, calculateRegionSides);
    assertEquals(output, 368);
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
    
    const output = partOne(input, calculateRegionSides);
    assertEquals(output, 1206);
});