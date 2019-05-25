/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentFundDeleteDialogComponent } from 'app/entities/provident-fund/provident-fund-delete-dialog.component';
import { ProvidentFundService } from 'app/entities/provident-fund/provident-fund.service';

describe('Component Tests', () => {
    describe('ProvidentFund Management Delete Component', () => {
        let comp: ProvidentFundDeleteDialogComponent;
        let fixture: ComponentFixture<ProvidentFundDeleteDialogComponent>;
        let service: ProvidentFundService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentFundDeleteDialogComponent]
            })
                .overrideTemplate(ProvidentFundDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProvidentFundDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvidentFundService);
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
