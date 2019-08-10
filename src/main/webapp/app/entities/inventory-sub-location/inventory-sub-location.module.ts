import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventorySubLocationComponent,
    InventorySubLocationDetailComponent,
    InventorySubLocationUpdateComponent,
    InventorySubLocationDeletePopupComponent,
    InventorySubLocationDeleteDialogComponent,
    inventorySubLocationRoute,
    inventorySubLocationPopupRoute
} from './';

const ENTITY_STATES = [...inventorySubLocationRoute, ...inventorySubLocationPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventorySubLocationComponent,
        InventorySubLocationDetailComponent,
        InventorySubLocationUpdateComponent,
        InventorySubLocationDeleteDialogComponent,
        InventorySubLocationDeletePopupComponent
    ],
    entryComponents: [
        InventorySubLocationComponent,
        InventorySubLocationUpdateComponent,
        InventorySubLocationDeleteDialogComponent,
        InventorySubLocationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventorySubLocationModule {}
