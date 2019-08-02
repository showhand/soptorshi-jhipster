import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IManufacturer } from 'app/shared/model/manufacturer.model';
import { ManufacturerService } from './manufacturer.service';

@Component({
    selector: 'jhi-manufacturer-update',
    templateUrl: './manufacturer-update.component.html'
})
export class ManufacturerUpdateComponent implements OnInit {
    manufacturer: IManufacturer;
    isSaving: boolean;

    constructor(protected manufacturerService: ManufacturerService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ manufacturer }) => {
            this.manufacturer = manufacturer;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.manufacturer.id !== undefined) {
            this.subscribeToSaveResponse(this.manufacturerService.update(this.manufacturer));
        } else {
            this.subscribeToSaveResponse(this.manufacturerService.create(this.manufacturer));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IManufacturer>>) {
        result.subscribe((res: HttpResponse<IManufacturer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
