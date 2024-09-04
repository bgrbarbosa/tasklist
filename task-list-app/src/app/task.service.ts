import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from './components/task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  apiURL: string = 'http://localhost:8082/task';

  constructor( private http: HttpClient ) { }

  getTasks() : Observable<Task[]> {
    return this.http.get<Task[]>(this.apiURL);
  }

  getByStatus(status: string) : Observable<Task[]> {
    return this.http.get<any>(`${this.apiURL}/status/${status}`);
  }

  getTaskById(id: number) : Observable<Task> {
    return this.http.get<any>(`${this.apiURL}/${id}`);
  }

  saveTask( task: Task ) : Observable<Task> {
    return this.http.post<Task>( `${this.apiURL}` , task);
  }

  updateTask( task: Task ) : Observable<any> {
    return this.http.put<Task>(`${this.apiURL}` , task);
  }

  deleteTask(task: Task) : Observable<any> {
    return this.http.delete<Task>(`${this.apiURL}/${task.idTask}`);
  }
}
