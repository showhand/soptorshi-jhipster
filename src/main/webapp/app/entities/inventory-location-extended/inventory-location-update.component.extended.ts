import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationServiceExtended } from './inventory-location.service.extended';
import { InventoryLocationUpdateComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-update-extended',
    templateUrl: './inventory-location-update.component.extended.html'
})
export class InventoryLocationUpdateComponentExtended extends InventoryLocationUpdateComponent implements OnInit {
    inventoryLocation: IInventoryLocation;
    isSaving: boolean;

    constructor(protected inventoryLocationService: InventoryLocationServiceExtended, protected activatedRoute: ActivatedRoute) {
        super(inventoryLocationService, activatedRoute);
    }

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
