module AggregateCrud
{
	// Show-cases handling of special and strange characters.
	aggregate Planet(name) {
		String name;
	}

	// Show-cases length limit on String type.
	aggregate Asteroid(code) {
		String(14) code;
	}
}
