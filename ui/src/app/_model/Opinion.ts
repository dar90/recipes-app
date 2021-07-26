import {User} from "./User";
import {Recipe} from "./Recipe";

export interface Opinion {
  id: number,
  content: string,
  created: Date,
  grade: string,
  author: User | null | undefined,
  recipe: Recipe | null | undefined
}
