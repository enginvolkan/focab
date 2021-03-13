export class MovieSearchResult {
	response: boolean;
	search: MovieDetails[];
	totalResults: number;

	constructor(response: boolean, search: MovieDetails[], totalResults: number) {
		this.response = response;
		this.search = search;
		this.totalResults = totalResults;
	}

}

export class MovieDetails {
	poster: string;
	title: string;
	type: string;
	year: string;
	imdbID: string;


	constructor(poster: string, title: string, type: string, year: string, imdbID: string) {
		this.poster = poster;
		this.title = title;
		this.type = type;
		this.year = year;
		this.imdbID = imdbID;
	}

}

export class EpisodeSearchResult {
	title: string;
	season: string;
	totalSeasons: string;
	episodes: Episode[];
	response: string;

}
export interface Episode {
	title: string;
	released: string;
	episode: string;
	imdbRating: string;
	imdbID: string;
}

export class MovieInfo {

	title: string;
	year: string;
	rated: string;
	released: string;
	season: string;
	episode: string;
	runtime: string;
	genre: string;
	director: string;
	writer: string;
	actors: string;
	plot: string;
	language: string;
	country: string;
	awards: string;
	poster: string;
	ratings: Rating[];
	metascore: string;
	imdbRating: string;
	imdbVotes: string;
	imdbID: string;
	seriesID: string;
	type: string;
	response: string;
}
export interface Rating {
	source: string;
	value: string;
}

export class MovieAnalysisResult {
	imdbId:string;
	idioms:Lexi[];
	phrasalVerbs:Lexi[];
	singleWords:Lexi[];
}

export class Lexi {
	text:string;
	definitions:string[];
	movieExamples:string[];
	otherExamples:string[];
}



