package battleship;

import java.util.*;

public abstract class Ship implements IShip {

	private interface ShipCreator {
		Ship create(Compass bearing, Position pos);
	}

	private static final String GALEAO = "galeao";
	private static final String FRAGATA = "fragata";
	private static final String NAU = "nau";
	private static final String CARAVELA = "caravela";
	private static final String BARCA = "barca";

	// ✅ Replace switch with polymorphism
	private static final Map<String, ShipCreator> shipFactory = Map.of(
			BARCA, Barge::new,
			CARAVELA, Caravel::new,
			NAU, Carrack::new,
			FRAGATA, Frigate::new,
			GALEAO, Galleon::new
	);

	static Ship buildShip(String shipKind, Compass bearing, Position pos) {
		assert shipKind != null;
		assert bearing != null;
		assert pos != null;

		ShipCreator creator = shipFactory.get(shipKind);
		return (creator != null) ? creator.create(bearing, pos) : null;
	}

	// ----------------------------

	private String category;
	private Compass bearing;
	private IPosition pos;
	private Integer size;
	protected List<IPosition> positions;

	public Ship(String category, Compass bearing, IPosition pos, int size) {
		this.category = Objects.requireNonNull(category);
		this.bearing = Objects.requireNonNull(bearing);
		this.pos = Objects.requireNonNull(pos);

		this.size = size;
		this.positions = new ArrayList<>();
	}

	@Override
	public String getCategory() {
		return category;
	}

	public List<IPosition> getPositions() {
		return positions;
	}

	public List<IPosition> getAdjacentPositions() {
		List<IPosition> adjacent = new ArrayList<>();

		for (IPosition p : positions) {
			for (IPosition adj : p.adjacentPositions()) {
				if (!positions.contains(adj) && !adjacent.contains(adj)) {
					adjacent.add(adj);
				}
			}
		}
		return adjacent;
	}

	@Override
	public IPosition getPosition() {
		return pos;
	}

	@Override
	public Compass getBearing() {
		return bearing;
	}

	public Integer getSize() {
		return size;
	}

	@Override
	public boolean stillFloating() {
		for (IPosition p : positions) {
			if (!p.isHit()) return true;
		}
		return false;
	}

	// ✅ Extract Method (remove duplicação)
	private int getExtremeRow(boolean min) {
		int value = positions.get(0).getRow();

		for (IPosition p : positions) {
			if (min && p.getRow() < value) value = p.getRow();
			if (!min && p.getRow() > value) value = p.getRow();
		}
		return value;
	}

	private int getExtremeColumn(boolean max) {
		int value = positions.get(0).getColumn();

		for (IPosition p : positions) {
			if (max && p.getColumn() > value) value = p.getColumn();
			if (!max && p.getColumn() < value) value = p.getColumn();
		}
		return value;
	}

	@Override
	public int getTopMostPos() {
		return getExtremeRow(true);
	}

	@Override
	public int getBottomMostPos() {
		return getExtremeRow(false);
	}

	@Override
	public int getRightMostPos() {
		return getExtremeColumn(true);
	}

	@Override
	public int getLeftMostPos() {
		return getExtremeColumn(false);
	}

	@Override
	public boolean occupies(IPosition pos) {
		assert pos != null;
		return positions.contains(pos);
	}

	@Override
	public boolean tooCloseTo(IShip other) {
		assert other != null;

		for (IPosition p : other.getPositions()) {
			if (tooCloseTo(p)) return true;
		}
		return false;
	}

	@Override
	public boolean tooCloseTo(IPosition pos) {
		assert pos != null;

		for (IPosition p : positions) {
			if (p.isAdjacentTo(pos)) return true;
		}
		return false;
	}

	@Override
	public void shoot(IPosition pos) {
		assert pos != null && pos.isInside();

		for (IPosition p : positions) {
			if (p.equals(pos)) {
				p.shoot();
			}
		}
	}

	@Override
	public void sink() {
		for (IPosition p : positions) {
			p.shoot();
		}
	}

	@Override
	public String toString() {
		return "[" + category + " " + bearing + " " + pos + "]";
	}
}