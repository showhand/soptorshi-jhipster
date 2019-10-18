import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventorySubLocationComponentExtended,
    InventorySubLocationDetailComponentExtended,
    InventorySubLocationUpdateComponentExtended,
    InventorySubLocationDeletePopupComponentExtended,
    InventorySubLocationDeleteDialogComponentExtended,
    inventorySubLocationRouteExtended,
    inventorySubLocationPopupRouteExtended
} from './';

const ENTITY_STATES = [...inventorySubLocationRouteExtended, ...inventorySubLocationPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventorySubLocationComponentExtended,
        InventorySubLocationDetailComponentExtended,
        InventorySubLocationUpdateComponentExtended,
        InventorySubLocationDeleteDialogComponentExtended,
        InventorySubLocationDeletePopupComponentExtended
    ],
    entryComponents: [
        InventorySubLocationComponentExtended,
        InventorySubLocationUpdateComponentExtended,
        InventorySubLocationDeleteDialogComponentExtended,
        InventorySubLocationDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventorySubLocationModuleExtended {}
