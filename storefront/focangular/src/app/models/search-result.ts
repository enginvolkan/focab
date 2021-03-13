export class SearchResult{
	word:string;
	score:number;
	tags:string[];
	defs:string[];

	public equals(word:string):boolean{
		if(this.word==word){
			return true;
		} else{
		return false;
		}
	}

	constructor(word:string,score:number){
		this.word = word;
		this.score=score;
	}

}