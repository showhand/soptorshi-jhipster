import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IProvidentManagement } from 'app/shared/model/provident-management.model';
import { ProvidentManagementService } from './provident-management.service';

@Component({
    selector: 'jhi-provident-management-update',
    templateUrl: './provident-management-update.component.html'
})
export class ProvidentManagementUpdateComponent implements OnInit {
    providentManagement: IProvidentManagement;
    isSaving: boolean;

    constructor(protected providentManagementService: ProvidentManagementService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ providentManagement }) => {
            this.providentManagement = providentManagement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.providentManagement.id !== undefined) {
            this.subscribeToSaveResponse(this.providentManagementService.update(this.providentManagement));
        } else {
            this.subscribeToSaveResponse(this.providentManagementService.create(this.providentManagement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvidentManagement>>) {
        result.subscribe((res: HttpResponse<IProvidentManagement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
