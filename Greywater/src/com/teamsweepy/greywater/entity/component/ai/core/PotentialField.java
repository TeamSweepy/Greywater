/**
 * Copyright Team Sweepy - Robin de Jong 2014 All use outside of the Greywater Project is not permitted unless express permission is
 * granted. Email TeamSweepy@gmail.com to discuss usage.
 */

package com.teamsweepy.greywater.entity.component.ai.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PotentialField extends Pathfinder<float[][]> {

	public PotentialField() {

	}

	@Override
	public float[][] create() {
		List<Point> obstacles = new ArrayList<Point>();

        float[][] generateMap = new float[map.length][map[0].length];

		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				Point currentPos = new Point(x, y);

				if (map[x][y] == 1) {
					obstacles.add(currentPos);
				} else {
					float distance = h(currentPos, end);
					generateMap[x][y] = cliff(distance);
				}
			}
		}

		for (Point obstacle : obstacles) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[0].length; y++) {
                    float newFloat = (float)Math.pow(0.5, h(new Point(x, y), obstacle)) / 20;

					if (x == obstacle.x && y == obstacle.y) {
						generateMap[x][y] = 0;
					} else {
						generateMap[x][y] = generateMap[x][y] - newFloat;
					}
				}
			}
		}

		return generateMap;
	}

	private float cliff(float value) {
		if (value == 0) {
			return 80;
		} else {
			return 80 / (value * value);
		}
	}

	@Override
	public void reset() {}

	@Override
	public boolean isGoal(Point from) {
		if (from == null || end == null) {
			return false;
		}
		return (from.x == end.x) && (from.y == end.y);
	}

	@Override
	protected float h(Point from, Point to) {
		float dx = from.x - to.x;
		float dy = from.y - to.y;

		return (float)Math.sqrt(dx * dx + dy * dy);
	}
}
