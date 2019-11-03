import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialInvoice, ICommercialInvoice } from 'app/shared/model/commercial-invoice.model';
import { CommercialInvoiceService } from './commercial-invoice.service';
import { CommercialInvoiceComponent } from './commercial-invoice.component';
import { CommercialInvoiceDetailComponent } from './commercial-invoice-detail.component';
import { CommercialInvoiceUpdateComponent } from './commercial-invoice-update.component';
import { CommercialInvoiceDeletePopupComponent } from './commercial-invoice-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialInvoiceResolve implements Resolve<ICommercialInvoice> {
    constructor(private service: CommercialInvoiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialInvoice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialInvoice>) => response.ok),
                map((commercialInvoice: HttpResponse<CommercialInvoice>) => commercialInvoice.body)
            );
        }
        return of(new CommercialInvoice());
    }
}

export const commercialInvoiceRoute: Routes = [
    {
        path: '',
        component: CommercialInvoiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialInvoiceDetailComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialInvoiceUpdateComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialInvoiceUpdateComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialInvoicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialInvoiceDeletePopupComponent,
        resolve: {
            commercialInvoice: CommercialInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
