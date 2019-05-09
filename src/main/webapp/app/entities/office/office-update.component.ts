import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IOffice } from 'app/shared/model/office.model';
import { OfficeService } from './office.service';

@Component({
    selector: 'jhi-office-update',
    templateUrl: './office-update.component.html'
})
export class OfficeUpdateComponent implements OnInit {
    office: IOffice;
    isSaving: boolean;

    constructor(protected officeService: OfficeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ office }) => {
            this.office = office;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.office.id !== undefined) {
            this.subscribeToSaveResponse(this.officeService.update(this.office));
        } else {
            this.subscribeToSaveResponse(this.officeService.create(this.office));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffice>>) {
        result.subscribe((res: HttpResponse<IOffice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
