/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { PurchaseCommitteeUpdateComponent } from 'app/entities/purchase-committee/purchase-committee-update.component';
import { PurchaseCommitteeService } from 'app/entities/purchase-committee/purchase-committee.service';
import { PurchaseCommittee } from 'app/shared/model/purchase-committee.model';

describe('Component Tests', () => {
    describe('PurchaseCommittee Management Update Component', () => {
        let comp: PurchaseCommitteeUpdateComponent;
        let fixture: ComponentFixture<PurchaseCommitteeUpdateComponent>;
        let service: PurchaseCommitteeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [PurchaseCommitteeUpdateComponent]
            })
                .overrideTemplate(PurchaseCommitteeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PurchaseCommitteeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PurchaseCommitteeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseCommittee(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseCommittee = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PurchaseCommittee();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.purchaseCommittee = entity;
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
