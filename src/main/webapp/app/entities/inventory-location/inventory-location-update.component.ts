import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationService } from './inventory-location.service';

@Component({
    selector: 'jhi-inventory-location-update',
    templateUrl: './inventory-location-update.component.html'
})
export class InventoryLocationUpdateComponent implements OnInit {
    inventoryLocation: IInventoryLocation;
    isSaving: boolean;

    constructor(protected inventoryLocationService: InventoryLocationService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ inventoryLocation }) => {
            this.inventoryLocation = inventoryLocation;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.inventoryLocation.id !== undefined) {
            this.subscribeToSaveResponse(this.inventoryLocationService.update(this.inventoryLocation));
        } else {
            this.subscribeToSaveResponse(this.inventoryLocationService.create(this.inventoryLocation));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryLocation>>) {
        result.subscribe((res: HttpResponse<IInventoryLocation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
