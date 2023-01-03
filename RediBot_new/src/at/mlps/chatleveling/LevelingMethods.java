package at.mlps.chatleveling;

public class LevelingMethods {
	
	/*
	 * Level 1 -> 10 : in 25pt Steps
	 * Level 11 -> 25 : in 50pt Steps
	 * Level 26 -> 100 : in 60-80pt Steps
	 * Level 101 -> indef : in 100pt Steps
	 */
	
	public int pointToLevel(long points) {
		int level = 0;
		if(points >= 0 && points <= 24) {
			level = 0;
		}else if(points >= 25 && points <= 49) {
			level = 1;
		}else if(points >= 50 && points <= 74) {
			level = 2;
		}else if(points >= 75 && points <= 99) {
			level = 3;
		}else if(points >= 100 && points <= 124) {
			level = 4;
		}else if(points >= 125 && points <= 149) {
			level = 5;
		}else if(points >= 150 && points <= 174) {
			level = 6;
		}else if(points >= 175 && points <= 199) {
			level = 7;
		}else if(points >= 200 && points <= 224) {
			level = 8;
		}else if(points >= 225 && points <= 249) {
			level = 9;
		}else if(points >= 250 && points <= 274) {
			level = 10;
		}else if(points >= 275 && points <= 324) {
			level = 11;
		}else if(points >= 325 && points <= 374) {
			level = 12;
		}else if(points >= 375 && points <= 424) {
			level = 13;
		}else if(points >= 425 && points <= 474) {
			level = 14;
		}else if(points >= 475 && points <= 524) {
			level = 15;
		}else if(points >= 525 && points <= 574) {
			level = 16;
		}else if(points >= 575 && points <= 624) {
			level = 17;
		}else if(points >= 625 && points <= 674) {
			level = 18;
		}else if(points >= 675 && points <= 724) {
			level = 19;
		}else if(points >= 725 && points <= 774) {
			level = 20;
		}else if(points >= 825 && points <= 874) {
			level = 21;
		}else if(points >= 875 && points <= 924) {
			level = 22;
		}else if(points >= 975 && points <= 1024) {
			level = 23;
		}else if(points >= 1025 && points <= 1074) {
			level = 24;
		}else if(points >= 1075 && points <= 1124) {
			level = 25;
		}
		return level;
	}

}
