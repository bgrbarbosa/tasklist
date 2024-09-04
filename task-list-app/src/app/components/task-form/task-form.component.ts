import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Task } from '../task';
import { TaskService } from 'src/app/task.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})

export class TaskFormComponent implements OnInit {

  task: Task;
  success: boolean = false;
  errors: String[] = [];
  id: number;

  constructor(
    private service: TaskService,
    private router: Router,
    private activatedRoute : ActivatedRoute
  ) { 
    this.task = new Task();
  }

  ngOnInit(): void {
    let params : Observable<Params> = this.activatedRoute.params
    params.subscribe( urlParams => {
        this.id = urlParams['id'];
        if(this.id){
          this.service
            .getTaskById(this.id)
            .subscribe( 
              response => this.task = response ,
              errorResponse => this.task = new Task()
            )
        }
    })
  }

  voltarParaListagem(){
    this.router.navigate([''])
  }

  onSubmit(){    
    if(this.id){
      this.service
        .updateTask(this.task)
        .subscribe(response => {
            this.success = true;
            this.errors = null;
        }, errorResponse => {
          this.errors = ['Erro ao atualizar o cliente.']
        })

    }else{
      this.service
        .saveTask(this.task)
          .subscribe( response => {
            this.success = true;
            this.errors = null;
            this.task = response;
          } , errorResponse => {
            this.success = false;
            this.errors = errorResponse.error.errors;
            console.log(this.errors)
          })
    }
  }
}
