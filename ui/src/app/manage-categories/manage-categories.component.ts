import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, Inject, ViewChild } from '@angular/core';
import { MatDialog, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AppSettings } from '../AppSettings';
import { Category } from '../_model/Category';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manage-categories',
  templateUrl: './manage-categories.component.html',
  styleUrls: ['./manage-categories.component.scss']
})
export class ManageCategoriesComponent implements AfterViewInit {

  categories: MatTableDataSource<Category>;
  displayedColumns: string[] = ['name', 'recipes', 'options'];
  filterValue: string = '';

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private httpClient: HttpClient,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) { }

  ngAfterViewInit(): void {
    this.httpClient.get<Category[]>(AppSettings.API_URL + '/category').subscribe(
      categories => {
        this.categories = new MatTableDataSource(categories);
        this.categories.paginator = this.paginator;
      }
    );
  }

  openDeleteDialog(category: Category): void {
    const dialogRef = this.dialog.open(DeleteCategoryDialog, {
      data: {
        name: category.name
      }
    });

    dialogRef.afterClosed().subscribe(
      result => {
        if(result === 'delete') {
          this.httpClient.delete(AppSettings.API_URL + '/category/' + category.name).subscribe(
            () => {
              this.snackBar.open(`Pomyślnie usunięto kategorię"${category.name}"`, 'OK', {duration: 3000});
              this.categories.data = this.categories.data.filter(
                cat => cat.name !== category.name
              );
            },
            () => this.snackBar.open('Nie udało się usunąć kategorii!', 'OK', {duration: 3000})
          )
        }
      }
    );
  }

  filterTable(): void {
    this.categories.filter = this.filterValue.trim().toLowerCase();
  }

}

@Component({
  selector: 'delete-category-dialog',
  templateUrl: 'delete-category-dialog.html'
})
export class DeleteCategoryDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<DeleteCategoryDialog>) {}
}
