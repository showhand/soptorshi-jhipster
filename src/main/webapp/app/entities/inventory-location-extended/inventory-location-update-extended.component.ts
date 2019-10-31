import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IInventoryLocation } from 'app/shared/model/inventory-location.model';
import { InventoryLocationExtendedService } from './inventory-location-extended.service';
import { InventoryLocationUpdateComponent } from 'app/entities/inventory-location';

@Component({
    selector: 'jhi-inventory-location-update-extended',
    templateUrl: './inventory-location-update-extended.component.html'
})
export class InventoryLocationUpdateExtendedComponent extends InventoryLocationUpdateComponent implements OnInit {
    inventoryLocation: IInventoryLocation;
    isSaving: boolean;

    constructor(protected inventoryLocationService: InventoryLocationExtendedService, protected activatedRoute: ActivatedRoute) {
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
