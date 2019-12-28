/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseOrderMessagesUpdateComponent } from 'app/entities/purchase-order-messages/purchase-order-messages-update.component';
import { PurchaseOrderMessagesService } from 'app/entities/purchase-order-messages/purchase-order-messages.service';
import { PurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

describe('Component Tests', () => {
    describe('PurchaseOrderMessages Management Update Component', () => {
        let comp: PurchaseOrderMessagesUpdateComponent;
        let fixture: ComponentFixture<PurchaseOrderMessagesUpdateComponent>;
        let service: PurchaseOrderMessagesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseOrderMessagesUpdateComponent]
            })
                .overrideTemplate(PurchaseOrderMessagesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseOrderMessagesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseOrderMessagesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseOrderMessages(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseOrderMessages = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseOrderMessages();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseOrderMessages = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
