export interface ApiResponse<T = unknown> {
  data?: T;
  status?: Status;
  error?: string | Record<string, string[]>;
  message?: string;
  http_status_code?: string;
  pagination?: Pagination;
}

export enum Status {
  TRUE = 'TRUE',
  FALSE = 'FALSE',
}

export interface Pagination {
  current_page: number;
  total_page: number;
}
