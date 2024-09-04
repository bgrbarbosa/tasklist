import { Component, OnInit } from '@angular/core';
import { Task } from '../task';
import { TaskService } from 'src/app/task.service';
import { Router } from '@angular/router'

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})

export class TaskListComponent implements OnInit {

  tasks: Task[] = [];
  taskSelected:Task;
  mensagemSucesso?: string;
  mensagemErro?: string;
  p: number = 1;
  sortDirection: boolean = true;
  currentSortField: string = '';


  constructor(
    private service: TaskService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.service
      .getTasks()
      .subscribe( resposta => this.tasks = resposta);
  }

  prepareDelete(task: Task){
    this.taskSelected = task;
  }

  deleteTask(){
    this.service
      .deleteTask(this.taskSelected)
      .subscribe( 
        response => {
          this.mensagemSucesso = 'Task deleted successfully!'
          this.ngOnInit();
        },
        erro => this.mensagemErro = 'An error occurred while deleting the task.'  
      )
  }

  findByStatus(status: string) {
    if (status === 'Clean Filter'){
      this.service.getTasks().subscribe(resposta => this.tasks = resposta);
    } else {
      this.service.getByStatus(status).subscribe(resposta => this.tasks = resposta); 
    }
    
  }

  sortData(field: string) {
    this.sortDirection = this.currentSortField === field ? !this.sortDirection : true; // Alterna a direção
    this.currentSortField = field; // Atualiza o campo de ordenação

    this.tasks.sort((a, b) => {
      const aField = a[field]; // obtém o valor do campo a
      const bField = b[field]; // obtém o valor do campo b

      if (typeof aField === 'string' && typeof bField === 'string') {
        return this.sortDirection ? aField.localeCompare(bField) : bField.localeCompare(aField);
      }
      return this.sortDirection ? aField - bField : bField - aField;
    });
  }

}
