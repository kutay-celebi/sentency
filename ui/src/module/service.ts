export interface ErrorResponse {
  errors: string[];
  args: Record<string, string>;
  code: string;
  uuid: string;
}

export interface JwtResponse {
  token: string;
  userId: string;
  role: "ADMIN" | "USER";
}

export interface WordDefinitionResponse {
  id: string;
  definition: string;
  definitionTr: string;
  examples: string[];
  synonyms: string[];
  partOfSpeech: string;
}

export interface WordResponse {
  id: string;
  word: string;
  definitions: WordDefinitionResponse[];
}

export interface UserWordRequest {
  wordId: string;
  userId: string;
  difficulty?: Difficulty;
}

export interface UserWordResponse {
  id: string;
  userId: string;
  wordId: string;
  word: string;
  nextReview: Date;
  lastReview: Date;
  difficulty?: Difficulty;
  count: number;
}

export interface SentenceRequest {
  wordId: string;
  userId: string;
  sentence: string;
  sentenceTr?: string;
}

export interface SentenceTranslateResponse {
  sentence: string;
  translation: string;
}

export interface SentencePersistResponse {
  id: string;
  wordId: string;
  userId: string;
  userWordId: string;
}

export type Difficulty = "EASY" | "MEDIUM" | "HARD";

export interface UserWordDifficultyRequest {
  userWordId: string;
  difficulty: Difficulty;
}
