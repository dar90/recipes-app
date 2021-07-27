import {Recipe} from "./Recipe";
import {Opinion} from "./Opinion";

export interface User {
  id: number,
  login: string,
  blocked: boolean,
  role: string,
  recipes: Recipe[],
  opinions: Opinion[]
}
