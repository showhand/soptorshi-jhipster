/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { DtTransactionUpdateComponent } from 'app/entities/dt-transaction/dt-transaction-update.component';
import { DtTransactionService } from 'app/entities/dt-transaction/dt-transaction.service';
import { DtTransaction } from 'app/shared/model/dt-transaction.model';

describe('Component Tests', () => {
    describe('DtTransaction Management Update Component', () => {
        let comp: DtTransactionUpdateComponent;
        let fixture: ComponentFixture<DtTransactionUpdateComponent>;
        let service: DtTransactionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DtTransactionUpdateComponent]
            })
                .overrideTemplate(DtTransactionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DtTransactionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DtTransactionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new DtTransaction(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dtTransaction = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new DtTransaction();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.dtTransaction = entity;
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
