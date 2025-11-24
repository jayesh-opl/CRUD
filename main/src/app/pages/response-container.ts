export interface ResponseContainer<T> {
  message: string;
  status: number;
  isError: boolean;
  body: T;
}