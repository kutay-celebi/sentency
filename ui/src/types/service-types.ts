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

export interface WordSynonymAntonymResponse {
  definition: string;
  word: string;
}

export interface WordDefinitionPhrasesResponse {
  definition: string;
  definitionOf: string;
  partOfSpeech: string;
}

export interface WordDefinitionResponse {
  id: string;
  examples: string[];
  synonyms: WordSynonymAntonymResponse[];
  antonyms: WordSynonymAntonymResponse[];
  phrases: Record<string, WordDefinitionPhrasesResponse>;
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
  isActive: boolean;
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

export interface SentencePageQueryRequest {
  userId: StringQueryItem;
  userWordId?: StringQueryItem;
  wordId?: StringQueryItem;
  word?: StringQueryItem;
  sorts?: SortItem[];
  size?: number;
  page?: number;
}

export interface SentenceResponse {
  id: string;
  sentence: string;
  wordId: string;
  word: string;
  difficulty: Difficulty;
  nextReview: Date;
  lastReview: Date;
}

export type Difficulty = "EASY" | "MEDIUM" | "HARD";

export interface UserWordDifficultyRequest {
  userWordId: string;
  difficulty: Difficulty;
}

export interface SortItem {
  field: string;
  direction: string;
}

export enum QueryItem {
  OPERATOR_EQUAL = ":",
  GREATER_THAN = ">=",
  GREATER = ">",
  LESS_THAN = "<=",
  LESS = "<",
  OPERATOR_NOT_EQUAL = "!:",
  OPERATOR_CONTAIN = "~",
  OPERATOR_NOT_CONTAIN = "!~",
}

export interface StringQueryItem {
  value: string;
  operator?: QueryItem;
}

export interface UserWordPageRequest {
  userId: StringQueryItem;
  word?: StringQueryItem;
  sorts?: SortItem[];
  size?: number;
  page?: number;
}

export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalPage: number;
}
