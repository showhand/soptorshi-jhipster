/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { TermsAndConditionsDeleteDialogComponent } from 'app/entities/terms-and-conditions/terms-and-conditions-delete-dialog.component';
import { TermsAndConditionsService } from 'app/entities/terms-and-conditions/terms-and-conditions.service';

describe('Component Tests', () => {
    describe('TermsAndConditions Management Delete Component', () => {
        let comp: TermsAndConditionsDeleteDialogComponent;
        let fixture: ComponentFixture<TermsAndConditionsDeleteDialogComponent>;
        let service: TermsAndConditionsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TermsAndConditionsDeleteDialogComponent]
            })
                .overrideTemplate(TermsAndConditionsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TermsAndConditionsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TermsAndConditionsService);
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
