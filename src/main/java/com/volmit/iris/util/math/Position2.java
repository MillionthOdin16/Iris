/*
 * Iris is a World Generator for Minecraft Bukkit Servers
 * Copyright (c) 2021 Arcane Arts (Volmit Software)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.volmit.iris.util.math;

public class Position2 {
    private int x;
    private int z;

    public Position2(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + z;
        return result;
    }

    public Position2 topLeftChunkOfRegion()
    {
        return new Position2((x >> 5) << 5, (z >> 5) << 5);
    }

    public Position2 bottomRightChunkOfRegion()
    {
        return new Position2((((x >> 5)+1) << 5) - 1, (((z >> 5)+1) << 5) - 1);
    }

    public Position2 topRightChunkOfRegion()
    {
        return new Position2((((x >> 5)+1) << 5) - 1, (z >> 5) << 5);
    }

    public Position2 bottomLeftChunkOfRegion()
    {
        return new Position2((x >> 5) << 5, (((z >> 5)+1) << 5) - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Position2 other)) {
            return false;
        }
        return x == other.x && z == other.z;
    }

    public double distance(Position2 center) {
        return Math.pow(center.getX() - x, 2) + Math.pow(center.getZ() - z, 2);
    }

    public Position2 add(int x, int z) {
        return new Position2(this.x + x, this.z + z);
    }
}
