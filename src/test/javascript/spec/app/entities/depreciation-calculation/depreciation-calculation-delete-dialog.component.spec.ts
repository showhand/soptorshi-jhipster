/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { DepreciationCalculationDeleteDialogComponent } from 'app/entities/depreciation-calculation/depreciation-calculation-delete-dialog.component';
import { DepreciationCalculationService } from 'app/entities/depreciation-calculation/depreciation-calculation.service';

describe('Component Tests', () => {
    describe('DepreciationCalculation Management Delete Component', () => {
        let comp: DepreciationCalculationDeleteDialogComponent;
        let fixture: ComponentFixture<DepreciationCalculationDeleteDialogComponent>;
        let service: DepreciationCalculationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DepreciationCalculationDeleteDialogComponent]
            })
                .overrideTemplate(DepreciationCalculationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepreciationCalculationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepreciationCalculationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
