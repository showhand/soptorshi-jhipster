import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from './designation.service';

@Component({
    selector: 'jhi-designation-update',
    templateUrl: './designation-update.component.html'
})
export class DesignationUpdateComponent implements OnInit {
    designation: IDesignation;
    isSaving: boolean;

    constructor(protected designationService: DesignationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ designation }) => {
            this.designation = designation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.designation.id !== undefined) {
            this.subscribeToSaveResponse(this.designationService.update(this.designation));
        } else {
            this.subscribeToSaveResponse(this.designationService.create(this.designation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDesignation>>) {
        result.subscribe((res: HttpResponse<IDesignation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
