export class SearchResult{
	word:string;
	score:number;
	tags:string[];
	defs:Definition[];

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

export class Definition{
	definition:string;
	tag:string;

	constructor(definition:string,tag:string){
		this.definition=definition;
		this.tag=tag;
	}
}