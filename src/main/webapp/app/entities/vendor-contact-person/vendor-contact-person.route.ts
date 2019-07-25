import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { VendorContactPersonService } from './vendor-contact-person.service';
import { VendorContactPersonComponent } from './vendor-contact-person.component';
import { VendorContactPersonDetailComponent } from './vendor-contact-person-detail.component';
import { VendorContactPersonUpdateComponent } from './vendor-contact-person-update.component';
import { VendorContactPersonDeletePopupComponent } from './vendor-contact-person-delete-dialog.component';
import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';

@Injectable({ providedIn: 'root' })
export class VendorContactPersonResolve implements Resolve<IVendorContactPerson> {
    constructor(public service: VendorContactPersonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVendorContactPerson> {
        const id = route.params['id'] ? route.params['id'] : null;
        const vendorId = route.params['vendorId'] ? route.params['vendorId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VendorContactPerson>) => response.ok),
                map((vendorContactPerson: HttpResponse<VendorContactPerson>) => vendorContactPerson.body)
            );
        } else if (vendorId) {
            const vendorContactPerson = new VendorContactPerson();
            vendorContactPerson.vendorId = vendorId;
            return of(vendorContactPerson);
        }
        return of(new VendorContactPerson());
    }
}

export const vendorContactPersonRoute: Routes = [
    {
        path: '',
        component: VendorContactPersonComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            defaultSort: 'id,asc',
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':vendorId/vendor',
        component: VendorContactPersonComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            defaultSort: 'id,asc',
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VendorContactPersonDetailComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':vendorId/new',
        component: VendorContactPersonUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VendorContactPersonUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VendorContactPersonUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendorContactPersonPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VendorContactPersonDeletePopupComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
